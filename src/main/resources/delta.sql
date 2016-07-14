-- Table 'Pessoa'
CREATE TABLE `jdbcdb`.`pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(65) NOT NULL,
  `profissao` VARCHAR(45) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;