-- sql 파일을 통해 어플리케이션 실행 시 자동으로 테이블에 레코드 3개 생성

insert into catalogs(product_id, product_name, stock, unit_price)
    values('CATALOG-001', 'Berlin', 100, 1500);
insert into catalogs(product_id, product_name, stock, unit_price)
    values('CATALOG-002', 'Tokyo', 110, 2000);
insert into catalogs(product_id, product_name, stock, unit_price)
    values('CATALOG-003', 'Seoul', 120, 2500);
