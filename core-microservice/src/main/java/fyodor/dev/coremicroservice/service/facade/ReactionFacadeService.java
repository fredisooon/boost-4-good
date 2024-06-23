package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.exception.ReactionNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UnauthorizedException;
import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.ReactionRepository;
import fyodor.dev.coremicroservice.rest.dto.ReactionDto;
import fyodor.dev.coremicroservice.rest.dto.ReactionSummaryDto;
import fyodor.dev.coremicroservice.rest.dto.request.ReactionRequest;
import fyodor.dev.coremicroservice.rest.mapper.ReactionMapper;
import fyodor.dev.coremicroservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionFacadeService {

    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final ReactionService reactionService;

    @Transactional
    public ReactionDto createReaction(String userLogin, ReactionRequest createReactionRequest) {
        log.info("Creating reaction for post ID: {}", createReactionRequest.getPostId());
        Reaction reaction = reactionService.createReaction(
                new Post(createReactionRequest.getPostId()),
                new User(userLogin),
                createReactionRequest.getType()
        );
        Reaction savedReaction = reactionRepository.save(reaction);
        log.info("Reaction created with ID: {}", savedReaction.getId());
        return reactionMapper.toDto(savedReaction);
    }

    @Transactional(readOnly = true)
    public ReactionDto getReaction(UUID reactionId) {
        log.info("Fetching reaction with ID: {}", reactionId);
        Reaction reaction = reactionRepository.findById(reactionId)
                .orElseThrow(() -> new ReactionNotFoundException("Reaction not found"));
        return reactionMapper.toDto(reaction);
    }

    @Transactional
    public void deleteReaction(String userLogin, UUID postId) {
        log.info("Deleting reaction from post ID: {}", postId);
        Reaction reaction = reactionRepository.findByPostIdAndUserUsername(postId, userLogin)
                .orElseThrow(() -> new ReactionNotFoundException("Reaction not found"));
        if (!reaction.getUser().getUsername().equals(userLogin)) {
            log.error("User not authorized to delete reaction from post ID: {}", postId);
            throw new UnauthorizedException("User not authorized to delete this reaction");
        }
        reactionRepository.delete(reaction);
        log.info("Reaction deleted from post ID: {}", postId);
    }

    @Transactional(readOnly = true)
    public List<ReactionSummaryDto> getReactionSummaryByPost(UUID postId) {
        log.info("Fetching reactions for post ID: {}", postId);
        Map<ReactionType, Long> reactionCounts = reactionRepository.findByPostId(postId).stream()
                .collect(Collectors.groupingBy(Reaction::getType, Collectors.counting()));

        return reactionCounts.entrySet().stream()
                .map(entry -> {
                    ReactionSummaryDto summary = new ReactionSummaryDto();
                    summary.setReactionType(entry.getKey());
                    summary.setCount(entry.getValue());
                    return summary;
                })
                .collect(Collectors.toList());
    }
}
