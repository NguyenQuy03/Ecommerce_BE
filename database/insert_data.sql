use `spring-boot-ecommerce`;

INSERT INTO role(code, name) VALUE("MANAGER", "manager");
INSERT INTO role(code, name) VALUE("AUDITADMIN", "audit-admin");
INSERT INTO role(code, name) VALUE("ACCOUNTADMIN", "account-admin");

INSERT INTO role(code, name) VALUE("BUYER", "buyer");
INSERT INTO role(code, name) VALUE("SELLER", "seller");

INSERT INTO account(username, password, fullname, status) VALUE("huyquy", "iX5sEx/WtKIhiOePjpTMo/RTqO8vp81L58xo8cz9qGc=", "nguyenhuyquy", true);	
INSERT INTO account(username, password, fullname, status) VALUE("nvb", "iX5sEx/WtKIhiOePjpTMo/RTqO8vp81L58xo8cz9qGc=", "nguyenvanb", true);
INSERT INTO account(username, password, fullname, status) VALUE("nvs", "iX5sEx/WtKIhiOePjpTMo/RTqO8vp81L58xo8cz9qGc=", "nguyenvans", true);

INSERT INTO accounts_roles(account_id, role_id) VALUE(1, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUE(2, 4);
INSERT INTO accounts_roles(account_id, role_id) VALUE(3, 5);