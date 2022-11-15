use `spring-boot-ecommerce`;

INSERT INTO role(code, name) VALUE("MANAGER", "manager");
INSERT INTO role(code, name) VALUE("AUDITADMIN", "audit-admin");
INSERT INTO role(code, name) VALUE("BUYER", "buyer");
INSERT INTO role(code, name) VALUE("SELLER", "seller");

INSERT INTO account(username, password, fullname, status) VALUE("huyquy", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Huy Quý", true);	
INSERT INTO account(username, password, fullname, status) VALUE("buyer", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Văn B", true);
INSERT INTO account(username, password, fullname, status) VALUE("seller1", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Tuấn S", true);
INSERT INTO account(username, password, fullname, status) VALUE("seller2", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Nguyễn Minh S", true);

INSERT INTO account(username, password, fullname, status) VALUE("audit", "$2a$12$t.e6fMXk7MdEjy9KzcYACe12RsU2vCLO4WH2RRAGBBGQ9mlk3Mdzu", "Trần Danh A", true);

INSERT INTO accounts_roles(account_id, role_id) VALUE(1, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUE(2, 3);
INSERT INTO accounts_roles(account_id, role_id) VALUE(3, 4);
INSERT INTO accounts_roles(account_id, role_id) VALUE(4, 2);

/*https://drive.google.com/drive/folders/1f1ee6tIXzgewkF9D1s2rAwy632oUiVXL?usp=sharing*/
INSERT INTO category(code, name) VALUE("women-clothes", "Women Clothes");
INSERT INTO category(code, name) VALUE("men-clothes", "Men Clothes");
INSERT INTO category(code, name) VALUE("beauty", "Beauty");
INSERT INTO category(code, name) VALUE("health", "Health");
INSERT INTO category(code, name) VALUE("mobile-gadgets", "Mobile & Gadgets");
INSERT INTO category(code, name) VALUE("books-magazines", "Books & Magazines");