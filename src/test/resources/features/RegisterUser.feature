# language: pt
@regressivo
Funcionalidade: Autenticação de usuário
  Como usuário da API
  Quero fazer login
  Para acessar a aplicação

  Cenário: Login bem-sucedido
    Dado que eu tenha os seguintes dados de login:
      | campo   | valor                  |
      | email   | usuario@exemplo.com    |
      | password   | senha123               |
    Quando eu enviar a requisição para o endpoint "/auth/login"
    Então o status code da resposta deve ser 200

  @padrão
  Cenário: Falha ao fazer login com credenciais inválidas
    Dado que eu tenha os seguintes dados de login:
      | campo   | valor                  |
      | email   | usuario@exemplo.com    |
      | password   | senhaerrada            |
    Quando eu enviar a requisição para o endpoint "/auth/login"
    Então o status code da resposta deve ser 401
    E o corpo de resposta de erro da API deve retornar a mensagem "Credenciais inválidas."

  @padrão
  Cenário: Registro de usuário bem-sucedido
    Dado que eu tenha os seguintes dados do usuário:
      | campo       | valor              |
      | name        | João Silva         |
      | email       | usuario@exemplo.com|
      | birthdate   | 2000-01-01         |
      | password    | senha123           |
    Quando eu enviar a requisição para o endpoint "/auth/register"
    Então o status code da resposta deve ser 201
