package com.hobbyproject.repository.post;

import com.hobbyproject.dto.post.request.PostSearchDto;
import com.hobbyproject.entity.Post;
import com.hobbyproject.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearchDto postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.postId.desc())
                .fetch();
    }

    @Override
    public long postCount() {
        return jpaQueryFactory.selectFrom(QPost.post).fetch().size();
    }

    @Override
    public void updateViewCount(Long postId, Long updatedCount) {
        jpaQueryFactory.update(QPost.post)
                .set(QPost.post.count,updatedCount)
                .where(QPost.post.postId.eq(postId))
                .execute();
    }
}
