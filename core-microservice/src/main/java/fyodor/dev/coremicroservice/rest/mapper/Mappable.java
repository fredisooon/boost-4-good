package fyodor.dev.coremicroservice.rest.mapper;


import java.util.List;

interface Mappable<E, D> {

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    E toEntity(D dto);

    List<E> toEntity(List<D> dtoes);
}
