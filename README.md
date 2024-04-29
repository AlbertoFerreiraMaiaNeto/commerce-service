# Commerce Service (commerce-service)

### Service for creating, reading, updating, and deleting products in the catalog, as well as processing orders.

-----
##  Funcionalidades Principais: 

### Gerenciamento de Produtos:

Adicione, atualize, recupere e remova produtos do catálogo.

### Gerenciamento de Categorias:
Ative ou desative categorias e ajuste a disponibilidade dos produtos.

### Validação de Categoria:
Produtos de categorias desativadas não são exibidos, e a própria categoria também é ocultada.

-----
### Tratamento de Erros
O catálogo de produtos implementa tratamento de erros para garantir a robustez do sistema. Aqui estão as exceções tratadas:


| Código de Erro | Mensagem de Erro |
| --------------:| ------------|
| C01	| Category not found.| 
| C02	| This Category Already Exists.| 
| P01	| Product not found. |
| P02	| This Product Already Exists |

-----
### Configurações:
As configurações do catálogo de produtos estão disponíveis no arquivo application.properties. 
Isso inclui configurações relacionadas ao Cache Caffeine e outras opções específicas do serviço.

-----
### Como Contribuir
Se deseja contribuir para o desenvolvimento ou correção de problemas, sinta-se à vontade para abrir problemas ou enviar pull requests. Certifique-se de seguir as diretrizes de contribuição do projeto.

Agradeço por escolher o meu catálogo de produtos. Espero que seja uma experiência valiosa.

-----
### Tecnologias Utilizadas:
Framework: Quarkus

Linguagem: Java

Banco de Dados: PostgreSQL

Cache: Caffeine


-----
Para acessar o Swagger, execute o projeto e digite no browser: http://localhost:8080/q/swagger-ui


