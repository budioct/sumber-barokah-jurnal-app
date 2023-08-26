insert into s_roles (id, name)
values ('r001', 'staff');

insert into s_roles (id, name)
values ('r002', 'manager');

insert into s_roles (id, name)
values ('r003', 'admin');

-- username : budhi
-- password : rahasia
insert into s_users (id, username, password, active)
values ('u001', 'budhi', '$2a$10$CM3UsAi9Miyos5rPqImbquiFsfexVx26RoxtBYr7TQvnKaW.5OLcy', true);

-- username : mamat
-- password : rahasia
insert into s_users (id, username, password, active)
values ('u002', 'mamat', '$2a$10$CM3UsAi9Miyos5rPqImbquiFsfexVx26RoxtBYr7TQvnKaW.5OLcy', true);

insert into s_permissions (id, permission_label, permission_value)
values ('p001', 'Lihat Data Transaksi', 'VIEW_TRANSAKSI');

insert into s_permissions (id, permission_label, permission_value)
values ('p002', 'Edit Data Transaksi', 'EDIT_TRANSAKSI');

insert into s_permissions (id, permission_label, permission_value)
values ('p003', 'Menambah Data Transaksi', 'POST_TRANSAKSI');

insert into s_permissions (id, permission_label, permission_value)
values ('p004', 'Hapus Data Transaksi', 'DELETE_TRANSAKSI');

insert into s_users_roles (id_user, id_role)
values ('u001', 'r001');

insert into s_users_roles (id_user, id_role)
values ('u002', 'r002');

insert into s_roles_permissions (id_role, id_permission)
values ('r001', 'p001');

insert into s_roles_permissions (id_role, id_permission)
values ('r002', 'p001');

insert into s_roles_permissions (id_role, id_permission)
values ('r002', 'p002');

insert into s_roles_permissions (id_role, id_permission)
values ('r003', 'p001');

insert into s_roles_permissions (id_role, id_permission)
values ('r003', 'p002');

insert into s_roles_permissions (id_role, id_permission)
values ('r003', 'p003');

insert into s_roles_permissions (id_role, id_permission)
values ('r003', 'p004');

-- client name : client1
-- client secret : abcd
-- grant type : authorization code with PKCE
insert into oauth2_registered_client (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
                                      client_name, client_authentication_methods, authorization_grant_types,
                                      redirect_uris,
                                      scopes,
                                      client_settings,
                                      token_settings)
values ('432f7b10-c3cd-4084-ac19-9ee068a7b435', 'client1', '2023-03-10 13:41:12.558667',
        '$2y$10$QmpDqxEmx0vdnZuvS8R3U.lCvLjwFGK44IFrbveDesIhgtvegC4im', null,
        'client1', 'client_secret_basic', 'authorization_code,refresh_token',
        'http://127.0.0.1:8000/login/oauth2/code/users-client-oidc,http://127.0.0.1:8000/authorized',
        'openid,profile,message.read,message.write',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');