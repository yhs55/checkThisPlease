create table todos (
                       id int primary key,
                       name VARCHAR(45),
                   completed BIGINT not null default false
);