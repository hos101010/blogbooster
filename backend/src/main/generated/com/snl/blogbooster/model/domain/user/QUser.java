package com.snl.blogbooster.model.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 265214611L;

    public static final QUser user = new QUser("user");

    public final com.snl.blogbooster.model.domain.QBaseEntity _super = new com.snl.blogbooster.model.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> auditAt = _super.auditAt;

    //inherited
    public final StringPath auditId = _super.auditId;

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final StringPath nickName = createString("nickName");

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

