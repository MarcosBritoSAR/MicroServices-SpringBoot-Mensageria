# Projeto de Microsserviços - Autenticação, Eureka, Gateway e Notificação com Rabbitmq

## 📋 Descrição do Projeto

Este projeto tem como objetivo demonstrar uma aplicação simples de microsserviços. A intenção é focar no aprendizado e na melhoria contínua da qualidade.

Observação: O projeto ainda não está completo, pois há funcionalidades adicionais planejadas. No entanto, há áreas que podem ser exploradas, fique à vontade para experimentar.

## 🛠 Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Cloud Eureka**
- **Spring Cloud Gateway**
- **RabbitMQ**
- **Postgres**
- **JWT Tokens**
- **Docker**
- **Maven**

Cada microsserviço desempenha um papel específico:

1. **Autenticação**: Gerencia autenticação de usuários.
2. **Eureka**: Atua como servidor de descoberta de serviços.
3. **Gateway**: Roteia e gerencia requisições entre os microsserviços.
4. **Notificação**: Deve ser responsável por enviar notificações para os usuários do sistema. (Ainda não faz isso hehe ;)

## ✅ Pré-requisitos

Para executar este projeto, você precisará:

1. **Maven** instalado ([Instalação do Maven](https://medium.com/@januario86/o-que-é-o-maven-e-como-instalar-1d5e9f29ac4c#:~:text=Para%20instalar%20o%20Maven%2C%20siga,variáveis%20de%20ambiente%20do%20Maven.)).
2. **Docker** instalado ([Instalação do Docker](https://www.docker.com/products/docker-desktop/)).
3. Um terminal compatível com **Bash** (caso esteja no Windows, você pode utilizar o [Git Bash](https://www.atlassian.com/git/tutorials/git-bash) ou [Windows Subsystem for Linux (WSL)](https://learn.microsoft.com/pt-br/windows/wsl/install)).

## 🚀 Como Rodar o Projeto

### Passo 1: Clone o repositório

``` bash
git clone https://github.com/MarcosBritoSAR/MicroServices-SpringBoot-Mensageria.git
cd MicroServices-SpringBoot-Mensageria
```

### Passo 2: Configure o Docker

Certifique-se de que o Docker está em execução e, se necessário, faça o login no Docker Hub:

``` bash
docker login
```

### Passo 3: Crie as imagens Docker e inicialize os containers

Este projeto possui arquivos **Dockerfile** , **docker-compose.yml** e um **run.sh** para facilitar o gerenciamento. Execute o seguinte comando para iniciar todos os serviços:

``` bash
source run.sh
```

### Passo 4: Verifique os serviços

Após iniciar os containers, você pode verificar se os microsserviços estão funcionando corretamente:

- Acesse a interface do **Eureka** pelo navegador em: `http://localhost:8761`
- **Gateway** estará disponível em: `http://localhost:8080`
- Os logs dos demais serviços estarão disponíveis no terminal onde você iniciou o Docker.

```
<projeto-raiz>
│
├── autenticacao/
│   ├── src/ ...
│   └── Dockerfile
│
├── eureka/
│   ├── src/ ...
│   └── Dockerfile
│
├── gateway/
│   ├── src/ ...
│   └── Dockerfile
│
├── notificacao/
│   ├── src/ ...
│   └── Dockerfile
│
└── docker-compose.yml
```

## 🖥 Exemplos de Endpoints

- **Autenticação**:
  - `POST /auth/token`: Realiza login do usuário.
  - `POST /user`: Registra um novo usuário.

- **Notificação**:
  - `POST /auth/token`: Envia uma mensagem para o micro servico de notificação (Óbvio que essa feature não está completa ainda. Olhe sempre a branch DEV pra ficar atulizado)

Os outros serviços (Eureka e Gateway) funcionarão apenas como gerenciadores/direcionadores no sistema.

## 🤝 Como Contribuir

Contribuições são sempre bem-vindas! Para contribuir:

1. Realize um fork do repositório.
2. Crie um branch para sua feature/correção:

``` bash
   git checkout -b minha-feature
```

1. Commit suas alterações:

``` bash
   git commit -m 'Adiciona minha nova feature'
```

1. Faça push para o branch:

``` bash
   git push origin minha-feature
```

1. Abra um Pull Request.
