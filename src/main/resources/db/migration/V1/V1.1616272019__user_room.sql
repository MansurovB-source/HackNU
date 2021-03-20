alter table og_user
    add column if not exists active_room_id bigint references room(id) on update cascade on delete no action;
