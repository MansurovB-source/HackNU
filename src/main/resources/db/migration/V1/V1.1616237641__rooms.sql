create table if not exists room (
    id             bigserial primary key ,
    created_at     timestamp,
    updated_at     timestamp,
    created_by     bigint references og_user(id) on update cascade on delete no action,
    name_room      varchar(255),
    description    text,
    counter        integer,
    type           integer,
    active         boolean
);

create table if not exists m2m_moderator_room (
    room_id  bigint references room(id) on update cascade on delete no action,
    user_id  bigint references og_user(id) on update cascade on delete no action
);

create table if not exists m2m_room_sit (
    room_id  bigint references room(id) on update cascade on delete no action,
    user_id  bigint references og_user(id) on update cascade on delete no action
);

create table if not exists m2m_subscribe (
    user_id  bigint references og_user(id) on update cascade on delete no action,
    user_subs_id  bigint references og_user(id) on update cascade on delete no action
);

create table if not exists messages (
    id bigserial primary key,
    created_at     timestamp,
    updated_at     timestamp,
    text text,
    user_id  bigint references og_user(id) on update cascade on delete no action,
    room_id  bigint references room(id) on update cascade on delete no action
);
