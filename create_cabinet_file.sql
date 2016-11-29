
CREATE TABLE IF NOT EXISTS `Dapoba_db`.`Cabinet` (

  `cabinet_number` VARCHAR(20) NOT NULL,

  `cabinet_state` VARCHAR(1) NOT NULL,

  `cabinet_password` VARCHAR(8) NULL,
`cabinet_loc` VARCHAR(2) NULL)

ENGINE = InnoDB;