create database if not exists gen4db;
grant all privileges on gen4db.* to gen4db@"%" identified by "gen4db";
grant all privileges on gen4db.* to gen4db@localhost identified by "gen4db";

-- Extra user/host privilege added to account for Fedora Core default hostname quirk.
grant all privileges on gen4db.* to gen4db@localhost.localdomain identified by "gen4db";