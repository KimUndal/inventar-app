
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

--####INDEXES ON INVENTORY TABLE
CREATE INDEX idx_inventoryType ON Inventory(inventoryType);
CREATE INDEX idx_category ON Inventory(category);
CREATE INDEX idx_dateOfPurchase ON Inventory(dateofpurchase);
CREATE INDEX idx_price ON Inventory(price);
CREATE INDEX idx_lifeExpectancy ON Inventory(lifeexpectancy);
CREATE INDEX idx_placement ON Inventory(placement);
CREATE INDEX idx_numberOfPurchase ON Inventory(numberofpurchase);

SELECT *,
       DATE(dateofpurchase, '+' || lifeexpectancy || ' years') AS expiryDate,
       CASE
           WHEN DATE('now') > DATE(dateofpurchase, '+' || lifeexpectancy || ' years') THEN 'taken out of use'
           ELSE 'in use'
           END AS usageStatus
FROM Inventory;
SELECT *
FROM Inventory
WHERE DATE('now') > DATE(dateofpurchase, '+' || lifeexpectancy || ' years');

CREATE TRIGGER IF NOT EXISTS update_dateChanged
    AFTER UPDATE OF statusCode, statusDescription ON InventoryStatus
    FOR EACH ROW
BEGIN
    UPDATE InventoryStatus
    SET dateChanged = DATE('now')
    WHERE statusId = OLD.statusId AND inventoryId = OLD.inventoryId;
END;

CREATE TRIGGER IF NOT EXISTS reset_description_on_status_change
    AFTER UPDATE OF statuscode ON InventoryStatus
    FOR EACH ROW
BEGIN
    UPDATE InventoryStatus
    SET statusDescription = CASE
                          WHEN OLD.statuscode = '500' AND NEW.statuscode = '200' THEN NULL
                          ELSE statusDescription
        END
    WHERE inventoryId = NEW.inventoryId;
    UPDATE InventoryStatus
    SET status = CASE
                     WHEN OLD.statuscode = '500' AND NEW.statuscode = '200' THEN 'In Use'
                     ELSE status
        END
    WHERE statusId = NEW.statusId AND inventoryId = NEW.inventoryId;
END;

CREATE TRIGGER SetStatusAfterInsert
    AFTER INSERT ON Inventory
    FOR EACH ROW
BEGIN
    INSERT INTO InventoryStatus (inventoryId, statuscode, status, dateChanged)
    VALUES (NEW.id, 200, 'In Use', DATE('now'));
END;
SELECT i.*
FROM Inventory i
         JOIN InventoryStatus s ON i.id = s.inventoryId
WHERE s.status = 'In Use';


drop trigger SetStatusAfterInsert;
drop trigger update_dateChanged;
drop trigger reset_description_on_status_change;