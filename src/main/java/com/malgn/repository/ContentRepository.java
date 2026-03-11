package com.malgn.repository;


import com.malgn.dto.ContentListDto;
import com.malgn.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("select new com.malgn.dto.ContentListDto(c.id, c.title, c.viewCount, c.createdBy, c.createdDate) from Content c")
    Page<ContentListDto> findAllForPage(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE contents set view_count = view_count + 1 where id = :id")
    int plusViewCount(@Param("id") Long id);

}
