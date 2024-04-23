package com.socialmedia.main.application.repository;

import com.socialmedia.main.application.util.SecurityUtils;
import com.socialmedia.main.domain.User;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>  {

    String WITHOUT_ID = "id!=%s";

    default Page<User> findAll(String search, Pageable pageable) {
        final String userId = SecurityUtils.getUserId();
        final String rsqlWithoutId = String.format(WITHOUT_ID, userId);
        final StringBuilder rsqlFilter = new StringBuilder(rsqlWithoutId);
        if (!search.isEmpty()) {
            rsqlFilter.append(" and ").append(URLDecoder.decode(search, Charset.defaultCharset()));
        }
        final Specification<User> specification = RSQLJPASupport.toSpecification(rsqlFilter.toString());
        return findAll(specification, pageable);
    }

}
