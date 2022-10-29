use `spring-boot-ecommerce`;

INSERT INTO role(code, name) VALUE("MANAGER", "manager");
INSERT INTO role(code, name) VALUE("AUDITADMIN", "audit-admin");
INSERT INTO role(code, name) VALUE("ACCOUNTADMIN", "account-admin");

INSERT INTO role(code, name) VALUE("BUYER", "buyer");
INSERT INTO role(code, name) VALUE("SELLER", "seller");

INSERT INTO account(username, password, fullname, status) VALUE("huyquy", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Huy Quý", true);	
INSERT INTO account(username, password, fullname, status) VALUE("buyer", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Văn B", true);
INSERT INTO account(username, password, fullname, status) VALUE("seller", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Tuấn S", true);
INSERT INTO account(username, password, fullname, status) VALUE("audit", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Trần Danh A", true);
INSERT INTO account(username, password, fullname, status) VALUE("account", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Bùi Thế C", true);

INSERT INTO accounts_roles(account_id, role_id) VALUE(1, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUE(2, 4);
INSERT INTO accounts_roles(account_id, role_id) VALUE(3, 5);
INSERT INTO accounts_roles(account_id, role_id) VALUE(4, 2);
INSERT INTO accounts_roles(account_id, role_id) VALUE(5, 3);