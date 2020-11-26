# PersonalProjectBI
## Personal Project with BI for metrics

Sistema Web no estilo Business Intelligence para organização e controle de projetos.

O Sistema foi desenvolvido usando Java 8 e os seguintes frameworks/tecnologias:

- JPA e Hibernate para persistência e validação dos dados
- Spring Security para autenticação e segurança
- Commons Email para enviar e-mails
- JSF e Primefaces no Frontend

O sistema é capaz de fazer um CRUD em dois tipos de projetos: Pessoal e Seletivo (Processos Seletivos que o usuário esteja participando). Cada projeto tem seus atributos em comum e também suas particularidades. 
Por exemplo, para o projeto pessoal, existe o atributo informando se o projeto foi colocado em produção ou não. 
Para o caso do seletivo, existem as informações da empresa, cidade e se foi aprovado ou não no Processo Seletivo.

Ainda no caso do processo seletivo, o sistema permite cadastrar informações da empresa responsável pelo processo, como nome, site, nome do contato na empresa e contato (e-mail ou telefone).
Com isso, todo processo seletivo tem uma empresa ligada a ele, como foi dito anteriormente.

O sistema também permite o CRUD dos usuários que irão acessar o sistema, contendo seus devidos "papéis" para acessos às páginas ou não.

Por fim, e mais importante, logo na homepage do sistema temos gráficos e dados para controle e estudo das informações a respeito de seus projetos, como por exemplo...

- Quantidade de projetos pessoais e seletivos
- Tempo total "gasto" com projetos finalizados
- Quantidade de projetos finalizados
- Porcentagem dos Projetos pessoais que estão e não em produção

...entre outros!

Com essas informações, o usuário pode acompanhar e verificar seus projetos.

Todos os dados foram gravados no Banco de dados PostgreSQL.

Esse sistema foi usado, testado e colocado em produção em um ambiente 100% AWS, usando os serviços Elastic Beanstalk e RDS do mesmo.

