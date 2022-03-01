package com.snl.blogbooster.model.domain.posting;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPosting is a Querydsl query type for Posting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPosting extends EntityPathBase<Posting> {

    private static final long serialVersionUID = 1651581723L;

    public static final QPosting posting = new QPosting("posting");

    public final com.snl.blogbooster.model.domain.QBaseEntity _super = new com.snl.blogbooster.model.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> auditAt = _super.auditAt;

    //inherited
    public final StringPath auditId = _super.auditId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> imageCount = createNumber("imageCount", Integer.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final StringPath postDate = createString("postDate");

    public final NumberPath<Long> postingNum = createNumber("postingNum", Long.class);

    public final StringPath registerUserId = createString("registerUserId");

    public final SetPath<com.snl.blogbooster.model.domain.tag.Tag, com.snl.blogbooster.model.domain.tag.QTag> tags = this.<com.snl.blogbooster.model.domain.tag.Tag, com.snl.blogbooster.model.domain.tag.QTag>createSet("tags", com.snl.blogbooster.model.domain.tag.Tag.class, com.snl.blogbooster.model.domain.tag.QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final NumberPath<Integer> videoCount = createNumber("videoCount", Integer.class);

    public final SetPath<com.snl.blogbooster.model.domain.video.Video, com.snl.blogbooster.model.domain.video.QVideo> videos = this.<com.snl.blogbooster.model.domain.video.Video, com.snl.blogbooster.model.domain.video.QVideo>createSet("videos", com.snl.blogbooster.model.domain.video.Video.class, com.snl.blogbooster.model.domain.video.QVideo.class, PathInits.DIRECT2);

    public final NumberPath<Integer> wordCount = createNumber("wordCount", Integer.class);

    public QPosting(String variable) {
        super(Posting.class, forVariable(variable));
    }

    public QPosting(Path<? extends Posting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPosting(PathMetadata metadata) {
        super(Posting.class, metadata);
    }

}

