package com.snl.blogbooster.model.domain.userScoreHistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserRankHistory is a Querydsl query type for UserScoreHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRankHistory extends EntityPathBase<UserScoreHistory> {

    private static final long serialVersionUID = 1030571153L;

    public static final QUserRankHistory userRankHistory = new QUserRankHistory("userRankHistory");

    public final StringPath category = createString("category");

    public final NumberPath<Integer> dayVisitor = createNumber("dayVisitor", Integer.class);

    public final NumberPath<Integer> monthPostingCount = createNumber("monthPostingCount", Integer.class);

    public final NumberPath<Integer> neighborCount = createNumber("neighborCount", Integer.class);

    public final StringPath standardDate = createString("standardDate");

    public final NumberPath<Integer> tagCount = createNumber("tagCount", Integer.class);

    public final NumberPath<Integer> totalPostingCount = createNumber("totalPostingCount", Integer.class);

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public final NumberPath<Integer> totalVisitor = createNumber("totalVisitor", Integer.class);

    public final StringPath userId = createString("userId");

    public QUserRankHistory(String variable) {
        super(UserScoreHistory.class, forVariable(variable));
    }

    public QUserRankHistory(Path<? extends UserScoreHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRankHistory(PathMetadata metadata) {
        super(UserScoreHistory.class, metadata);
    }

}

