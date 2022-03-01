package com.snl.blogbooster.model.domain.userScoreHistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserScoreHistory is a Querydsl query type for UserScoreHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserScoreHistory extends EntityPathBase<UserScoreHistory> {

    private static final long serialVersionUID = -1080349869L;

    public static final QUserScoreHistory userScoreHistory = new QUserScoreHistory("userScoreHistory");

    public final NumberPath<Long> dayVisitor = createNumber("dayVisitor", Long.class);

    public final NumberPath<Long> monthPostingCount = createNumber("monthPostingCount", Long.class);

    public final NumberPath<Long> neighborCount = createNumber("neighborCount", Long.class);

    public final StringPath standardDate = createString("standardDate");

    public final NumberPath<Long> tagCount = createNumber("tagCount", Long.class);

    public final NumberPath<Long> totalPostingCount = createNumber("totalPostingCount", Long.class);

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public final NumberPath<Long> totalVisitor = createNumber("totalVisitor", Long.class);

    public final StringPath userId = createString("userId");

    public QUserScoreHistory(String variable) {
        super(UserScoreHistory.class, forVariable(variable));
    }

    public QUserScoreHistory(Path<? extends UserScoreHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserScoreHistory(PathMetadata metadata) {
        super(UserScoreHistory.class, metadata);
    }

}

