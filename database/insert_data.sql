use `spring-boot-ecommerce`;

INSERT INTO role(code, name) VALUE("MANAGER", "manager");
INSERT INTO role(code, name) VALUE("AUDITADMIN", "audit-admin");
INSERT INTO role(code, name) VALUE("ACCOUNTADMIN", "account-admin");

INSERT INTO role(code, name) VALUE("BUYER", "buyer");
INSERT INTO role(code, name) VALUE("SELLER", "seller");

INSERT INTO account(username, password, fullname, status) VALUE("huyquy", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "nguyenhuyquy", true);	
INSERT INTO account(username, password, fullname, status) VALUE("nvb", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "nguyenvanb", true);
INSERT INTO account(username, password, fullname, status) VALUE("nvs", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "nguyenvans", true);

INSERT INTO accounts_roles(account_id, role_id) VALUE(1, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUE(2, 4);
INSERT INTO accounts_roles(account_id, role_id) VALUE(3, 5);