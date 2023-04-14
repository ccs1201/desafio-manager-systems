# Desafio Técnico Manager Systems

### Sobre este projeto
###### Autor: Cleber C. Souza (13/04/2023)

### Documentação da API acessível em ***/api-doc.html***

### Endpoints em /api/{versao}:
 
*  v1 - Utilizando os métodos padrão sugeridos no documento do desafio.
*  v2 - Utilizando os métodos, verbos e ResponseStatus HTTTP descritos no padrão REST.
    * * [HTTP Methods](https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Methods)
    * * [Representational State Transfer REST](https://www.service-architecture.com/articles/web-services/representational-state-transfer-rest.html)
* Classe ***com.ccs.core.utils.data.LoadData*** popula o banco na inicialização da aplicação.
* Foi utilizado Beans Validation para os os ***MODEL INPUTS*** da ***API***.
* A duração do ***TOKEN*** é definida no ***application.properties*** no atributo ***com.ccs.api.token_duration***.
* Todos os endpoints são assíncronos, para testes de escalabilidade sugiro o [K6](https://k6.io/) da Grafana Labs.
* Os ***end points*** de listagem são ***paginados***.
* Banco de dados H2 em ***memória***, como sugerido no documento do desafio. 
* Teste ***Unitários*** ou de ***integração*** não foram solicitados no documento do desafio.