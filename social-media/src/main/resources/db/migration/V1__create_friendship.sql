CREATE TABLE IF NOT EXISTS friendship (
    id UUID PRIMARY KEY,
    id_friend VARCHAR(36) NOT NULL,
    id_requester VARCHAR(36) NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT FK_FRIEND FOREIGN KEY (id_friend) REFERENCES keycloak.user_entity(id),
    CONSTRAINT FK_FRIEND_REQUESTER FOREIGN KEY (id_requester) REFERENCES keycloak.user_entity(id)
);