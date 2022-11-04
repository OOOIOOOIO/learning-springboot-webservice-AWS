package com.sh.service.posts;

import com.sh.web.domain.posts.Posts;
import com.sh.web.domain.posts.PostsRepository;
import com.sh.web.dto.PostsListResponseDTO;
import com.sh.web.dto.PostsResponseDTO;
import com.sh.web.dto.PostsSaveRequestDTO;
import com.sh.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    // 등록
    @Transactional
    public Long save(PostsSaveRequestDTO requestDTO){

        return postsRepository.save(requestDTO.toEntity()).getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDTO requestDTO) {
        // select 해와서
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        
        // repository에는 update 메서드가 없기 때문에
        // Entity 클래스에서 직접 구현해준다.(바로 컬럼을 this.~로 건들 수 있기 때문이지)
        posts.update(requestDTO.getTitle(), requestDTO.getContent());

        return id;
    }

    // 조회
    public PostsResponseDTO findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id" + id));

        // entity --> DTO
        return new PostsResponseDTO(entity);
    }

    // readOnly : 트랜잭션은 유지하되 "조회" 기능만 남겨두어 조회 속도가 개선된다.
    @Transactional(readOnly = true)
    public List<PostsListResponseDTO> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDTO::new)// == .map(posts -> new PostsListResponseDTO(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));


        postsRepository.delete(posts);
    }

}
