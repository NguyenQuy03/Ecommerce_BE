use `spring-boot-ecommerce`;

INSERT INTO role(code, name) VALUE("MANAGER", "manager");
INSERT INTO role(code, name) VALUE("BUYER", "buyer");
INSERT INTO role(code, name) VALUE("SELLER", "seller");

INSERT INTO account(username, password, fullname, status) VALUE("huyquy", "$2a$10$22dP2Tp4lh19L9qRpn0Ye.fXuahHtYDFgpJYpz3iA2SIqw75cuAJO", "Nguyễn Huy Quý", true);	
INSERT INTO account(username, password, fullname, status) VALUE("buyer", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Văn B", true);
INSERT INTO account(username, password, fullname, status) VALUE("menClothes", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Tuấn S", true);
INSERT INTO account(username, password, fullname, status) VALUE("womenClothes", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Minh S", true);

INSERT INTO accounts_roles(account_id, role_code) VALUE(1, "MANAGER");
INSERT INTO accounts_roles(account_id, role_code) VALUE(2, "BUYER");
INSERT INTO accounts_roles(account_id, role_code) VALUE(3, "SELLER");
INSERT INTO accounts_roles(account_id, role_code) VALUE(4, "SELLER");