pg_dump -U postgres -h localhost -p 5432 -Fc -f backup.dmp matelas2

pg_restore -U postgres -h localhost -p 5432 -d matelas2 backup.dmp
        ETU002554_2024