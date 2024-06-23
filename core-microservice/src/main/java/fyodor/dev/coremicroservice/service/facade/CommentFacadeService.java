package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UnauthorizedException;
import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.CommentRepository;
import fyodor.dev.coremicroservice.rest.dto.CommentDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateCommentRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateCommentRequest;
import fyodor.dev.coremicroservice.rest.mapper.CommentMapper;
import fyodor.dev.coremicroservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentFacadeService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @Transactional
    public CommentDto createComment(String userLogin, CreateCommentRequest createCommentRequest) {
        log.info("Creating comment for post ID: {}", createCommentRequest.getPostId());
        Comment comment = commentService.createComment(new Post(createCommentRequest.getPostId()), new User(userLogin), createCommentRequest.getContent());
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment created with ID: {}", savedComment.getId());
        return commentMapper.toDto(savedComment);
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(UUID commentId) {
        log.info("Fetching comment with ID: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateComment(String userLogin, UUID commentId, UpdateCommentRequest updateCommentRequest) {
        log.info("Updating comment with ID: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUser().getUsername().equals(userLogin)) {
            log.error("User not authorized to update comment with ID: {}", commentId);
            throw new UnauthorizedException("User not authorized to update this comment");
        }
        commentService.updateComment(comment, updateCommentRequest.getContent());
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment updated with ID: {}", updatedComment.getId());
        return commentMapper.toDto(updatedComment);
    }

    @Transactional
    public void deleteComment(String userLogin, UUID commentId) {
        log.info("Deleting comment with ID: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUser().getUsername().equals(userLogin)) {
            log.error("User not authorized to delete comment with ID: {}", commentId);
            throw new UnauthorizedException("User not authorized to delete this comment");
        }
        commentRepository.delete(comment);
        log.info("Comment deleted with ID: {}", commentId);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPost(UUID postId) {
        log.info("Fetching comments for post ID: {}", postId);
        return commentRepository.findByPostId(postId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
