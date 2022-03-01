package com.snl.blogbooster.model.domain.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1657442613L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTag tag = new QTag("tag");

    public final StringPath content = createString("content");

    public final com.snl.blogbooster.model.domain.posting.QPosting posting;

    public final NumberPath<Long> postingNum = createNumber("postingNum", Long.class);

    public final NumberPath<Long> tagNum = createNumber("tagNum", Long.class);

    public final StringPath tagType = createString("tagType");

    public final com.snl.blogbooster.model.domain.video.QVideo video;

    public final NumberPath<Long> videoNum = createNumber("videoNum", Long.class);

    public QTag(String variable) {
        this(Tag.class, forVariable(variable), INITS);
    }

    public QTag(Path<? extends Tag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTag(PathMetadata metadata, PathInits inits) {
        this(Tag.class, metadata, inits);
    }

    public QTag(Class<? extends Tag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.posting = inits.isInitialized("posting") ? new com.snl.blogbooster.model.domain.posting.QPosting(forProperty("posting")) : null;
        this.video = inits.isInitialized("video") ? new com.snl.blogbooster.model.domain.video.QVideo(forProperty("video"), inits.get("video")) : null;
    }

}

