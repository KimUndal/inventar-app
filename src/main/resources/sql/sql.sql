
--#################TRIGGER TO INSERT ROW IN INVENTORY TABLE WHEN A ROW IS INSERTED IN TABLE MOBEL,
--#################TEKNISKUTSTYR OR UTSMYKNING.
CREATE TRIGGER AutoGenerateUUID_Mobel
    AFTER INSERT ON Mobel
    FOR EACH ROW
BEGIN
    INSERT INTO inventorylog (id, mobel) VALUES (
                                              (SELECT hex(randomblob(4)) || '-' || hex(randomblob(2))
                                                          || '-' || '4' || substr(hex(randomblob(2)), 2) || '-'
                                                          || substr('AB89', 1 + (abs(random()) % 4), 1)  ||
                                                      substr(hex(randomblob(2)), 2) || '-' || hex(randomblob(6)))
                                            , NEW.id
                                            );
END;

CREATE TRIGGER AutoGenerateUUID_TekniskUtstyr
    AFTER INSERT ON TekniskUtstyr
    FOR EACH ROW
BEGIN
    INSERT INTO inventorylog (id, tekniskutstyr) VALUES (
                                                      (SELECT hex(randomblob(4)) || '-' || hex(randomblob(2))
                                                                  || '-' || '4' || substr(hex(randomblob(2)), 2) || '-'
                                                                  || substr('AB89', 1 + (abs(random()) % 4), 1)  ||
                                                              substr(hex(randomblob(2)), 2) || '-' || hex(randomblob(6)))
                                                    , NEW.id
                                                    );
END;

CREATE TRIGGER AutoGenerateUUID_Utsmykning
    AFTER INSERT ON Utsmykning
    FOR EACH ROW
BEGIN
    INSERT INTO inventorylog (id, utsmykning) VALUES (
                                                   (SELECT hex(randomblob(4)) || '-' || hex(randomblob(2))
                                                               || '-' || '4' || substr(hex(randomblob(2)), 2) || '-'
                                                               || substr('AB89', 1 + (abs(random()) % 4), 1)  ||
                                                           substr(hex(randomblob(2)), 2) || '-' || hex(randomblob(6)))
                                                 , NEW.id
                                                 );
END;
--drop trigger if exists AutoGenerateUUID_Mobel;
--drop trigger if exists AutoGenerateUUID_TekniskUtstyr;
--drop trigger if exists AutoGenerateUUID_Utsmykning;
