package com.snl.blogbooster.model.domain.video;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideo is a Querydsl query type for Video
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideo extends EntityPathBase<Video> {

    private static final long serialVersionUID = -1322901939L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideo video = new QVideo("video");

    public final StringPath description = createString("description");

    public final com.snl.blogbooster.model.domain.posting.QPosting posting;

    public final NumberPath<Long> postingNum = createNumber("postingNum", Long.class);

    public final SetPath<com.snl.blogbooster.model.domain.tag.Tag, com.snl.blogbooster.model.domain.tag.QTag> tags = this.<com.snl.blogbooster.model.domain.tag.Tag, com.snl.blogbooster.model.domain.tag.QTag>createSet("tags", com.snl.blogbooster.model.domain.tag.Tag.class, com.snl.blogbooster.model.domain.tag.QTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Long> videoNum = createNumber("videoNum", Long.class);

    public QVideo(String variable) {
        this(Video.class, forVariable(variable), INITS);
    }

    public QVideo(Path<? extends Video> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideo(PathMetadata metadata, PathInits inits) {
        this(Video.class, metadata, inits);
    }

    public QVideo(Class<? extends Video> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.posting = inits.isInitialized("posting") ? new com.snl.blogbooster.model.domain.posting.QPosting(forProperty("posting")) : null;
    }

}

