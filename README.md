# Archetype Spring boot Mvc

## Objetivo

Repositório template de uma arquitetura MVC com Spring Boot, já configurados classes
abstratas e interfaces padrão para o desenvolvimento padronizado.

## Utilizar o projeto como Template

### Terminal
    mvn archetype:generate 
    -DarchetypeGroupId=com.lucasmartins 
    -DarchetypeArtifactId=archetype-spring-mvc 
    -DarchetypeVersion=0.0.1-SNAPSHOT 
    -DgroupId=<groupId-novo-projeto> 
    -DartifactId=<artifactId-novo-projeto> 
    -Dversion=0.1.0-SNAPSHOT 
    -Dpackage=br.com.your.domain
(Deve ser executado em uma linha, a maneira que foi escrito acima é 
somente para melhor entendimento)


### Intellij
- File -> New -> Project -> Maven Archetype
- Inserir o nome do novo projeto no campo Name
- Identificar a localização do novo projeto
- Escolher a JDK que irá ser utilizada no projeto
- No campo archetype, ir em ADD, e selecionar esse projeto como modelo

### Padrões do projeto


- Services:
    - Devem possuir uma interface para exposição de seus métodos públicos.
    - A interface deve estender a interface ``IAbstractService``
    - Devem extender a classe abstrata ``AbstractServiceImpl``
      <br><br>

- Controllers
    - Devem posssuir uma interface para separar a documentação e mapeamento de endpoints do código dos métodos.
    - Devem extender a classe abstrata ``AbstractController``.
    - A documentação swagger deverá referenciar o documento de configuração ``application-swagger.yaml``.
      <br><br>
- Repositories:
    - Devem possuir uma interface customizada com o prefixo ``custom``.
    - Essa interface servirá para realizar a ligação dos métodos do ``Repository`` e do ``RepositoryImpl``, com ambas as classes implementando a customização.
    - Caso necessário, no package de specs, deverão ser criadas as especificações de consulta de cada entidade.
      <br><br>
        - Specifications
            - O nome dos campos das entidade deverão estar declarados em constantes estáticas.
            - Deverão possuir somente métodos de consulta estáticos. <br><br>

        - Implementações
            - Deverão estender a classe abstrata ``GenericImpl`` e implementar a interface customizada referente a entidade de consulta.
            - Quando possível, deverão realizar consultas com ``CriteriaQuery`` utilizando o método ``getSpecRepository()``, e referenciando uma Specification da entidade consultada.
            - Caso não seja possível utilizar ``CriteriaQuery``, as consultas devem ser realizadas com ``JPQL``, utilizando os métodos disponíveis na classe ``GenericImpl``.
              <br><br>
 
