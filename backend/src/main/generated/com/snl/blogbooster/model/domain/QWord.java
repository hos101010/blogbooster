package com.snl.blogbooster.model.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWord is a Querydsl query type for Word
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWord extends EntityPathBase<Word> {

    private static final long serialVersionUID = -1594127963L;

    public static final QWord word = new QWord("word");

    public final StringPath registerUserId = createString("registerUserId");

    public final NumberPath<Long> repeatCount = createNumber("repeatCount", Long.class);

    public final StringPath url = createString("url");

    public final StringPath value = createString("value");

    public final NumberPath<Long> wordNum = createNumber("wordNum", Long.class);

    public final StringPath wordType = createString("wordType");

    public QWord(String variable) {
        super(Word.class, forVariable(variable));
    }

    public QWord(Path<? extends Word> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWord(PathMetadata metadata) {
        super(Word.class, metadata);
    }

}

