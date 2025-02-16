# Render 3D Simples

Este é um projeto Java que demonstra uma renderização 3D simples utilizando Swing.
O aplicativo permite visualizar e interagir com formas 3D (como Cubo, Pirâmide, Retângulo e Prisma),
possibilitando rotações, zoom, preenchimento de faces com escolha de cor e demarcação das arestas com cor fixa.

## Funcionalidades

- **Renderização de Formas 3D:** Cubo, Pirâmide, Retângulo e Prisma.
- **Interação via Mouse:**  
  - *Arraste o mouse* para rotacionar a forma.  
  - *Roda do mouse* para ajustar o zoom.
- **Opções de Renderização:**  
  - Ativar/Desativar preenchimento de faces.
  - Escolher a cor do preenchimento via seletor de cores.
  - Ativar/Desativar demarcação das arestas (com cor fixa preta).

## Pré-Requisitos

- **Java JDK 17** ou superior
- **Maven**

## Como Rodar

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/java-renderizador-3d.git
   cd java-renderizador-3d
    ```
   
2. **Compile e empacote o projeto:**

    ```bash
    mvn clean package
    ```
   
3. **Execute o aplicativo:**

    ```bash
    java -jar target/render-1.0-SNAPSHOT.jar
    ```
   
## Como Usar
Ao iniciar o aplicativo, a janela exibirá uma forma 3D (o Cubo é exibido por padrão).
Na parte inferior, você encontrará os controles:

- **Seleção de Forma:** Utilize os botões de rádio para trocar entre Cubo, Pirâmide, Retângulo e Prisma.
- **Preencher Faces:**
- - Marque o checkbox "Preencher Faces" para ativar o preenchimento dos polígonos.
- - Clique em "Escolher Cor" para selecionar a cor desejada (o botão fica ativo somente quando o preenchimento está ativado).
- **Marcar Arestas:**
- - Marque o checkbox "Marcar Arestas" para desenhar as arestas em preto.

Você pode rotacionar a forma clicando e arrastando o mouse sobre a área de renderização
e usar a roda do mouse para aproximar ou afastar.