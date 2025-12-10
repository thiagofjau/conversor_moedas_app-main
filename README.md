# ConversorMoedasApp 💱

## 📱 Sobre o Projeto

Aplicativo Android de conversão de moedas desenvolvido em Java para a disciplina PDM1 (Programação para Dispositivos Móveis I) da FATEC Jahu.

O aplicativo permite converter valores em Reais (BRL) para outras moedas internacionais utilizando taxas de câmbio em tempo real.

## 🎯 Funcionalidades

- ✅ Conversão de Real (BRL) para múltiplas moedas:
  - USD - Dólar Americano
  - EUR - Euro
  - GBP - Libra Esterlina
  - JPY - Iene Japonês
  - ARS - Peso Argentino
  - CAD - Dólar Canadense

- ✅ Taxas de câmbio em tempo real via API AwesomeAPI
- ✅ Interface intuitiva e responsiva
- ✅ Validação de entrada de dados
- ✅ Exibição da taxa de conversão utilizada

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java
- **IDE:** Android Studio
- **API:** AwesomeAPI (https://economia.awesomeapi.com.br)
- **Bibliotecas:**
  - AndroidX AppCompat 1.6.1
  - Material Components 1.11.0
  - ConstraintLayout 2.1.4
  - OkHttp 4.12.0
  - JSON 20231013

## 📋 Requisitos do Sistema

- **Android Studio:** Arctic Fox ou superior
- **JDK:** 11 ou superior
- **Android SDK:**
  - Minimum SDK: API 24 (Android 7.0 - Nougat)
  - Target SDK: API 36
  - Compile SDK: 36
- **Gradle:** 8.11.2 ou superior
- **Conexão com a Internet:** Necessária para obter taxas de câmbio

## 🚀 Como Executar o Projeto

### 1. Clonar ou Baixar o Projeto

Se ainda não tiver o projeto, baixe ou clone este repositório.

### 2. Abrir no Android Studio

1. Abra o Android Studio
2. Selecione "Open an Existing Project"
3. Navegue até a pasta do projeto e selecione-a
4. Aguarde o Gradle Sync completar

### 3. Configurar Emulador ou Dispositivo Físico

#### Opção A - Emulador:
1. No Android Studio, vá em: Tools > Device Manager
2. Crie um novo dispositivo virtual (AVD)
3. Recomendado: Pixel 6 com Android 13 (API 33) ou superior

#### Opção B - Dispositivo Físico:
1. Habilite "Opções do Desenvolvedor" no seu dispositivo Android
2. Ative "Depuração USB"
3. Conecte o dispositivo via USB
4. Autorize a depuração USB quando solicitado

### 4. Executar o Aplicativo

1. Certifique-se de que o emulador está rodando ou o dispositivo está conectado
2. Clique no botão "Run" (ícone de play verde) ou pressione Shift+F10
3. Selecione o dispositivo/emulador de destino
4. Aguarde a compilação e instalação

## 📝 Como Usar o Aplicativo

1. **Inserir Valor:** Digite o valor em Reais (R$) que deseja converter
2. **Selecionar Moeda:** Escolha a moeda de destino no menu suspenso
3. **Converter:** Clique no botão "CONVERTER"
4. **Visualizar Resultado:** O valor convertido e a taxa de câmbio serão exibidos

## 📁 Estrutura do Projeto

```
conversor_moedas_app-main/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/br/edu/fatecjahu/conversormoedasapp/
│   │   │   │   └── MainActivity.java          # Lógica principal do app
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml      # Layout da interface
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml            # Strings do app
│   │   │   │   │   ├── colors.xml             # Cores
│   │   │   │   │   └── themes.xml             # Temas
│   │   │   └── AndroidManifest.xml            # Manifesto do app
│   │   ├── androidTest/                       # Testes instrumentados
│   │   └── test/                              # Testes unitários
│   └── build.gradle                           # Configurações do módulo
├── gradle/
│   └── libs.versions.toml                     # Versões das dependências
├── build.gradle                               # Configurações do projeto
├── settings.gradle                            # Configurações do Gradle
└── README.md                                  # Este arquivo
```

## 🔧 Configurações Importantes

### build.gradle (Module: app)

```gradle
dependencies {
    implementation libs.appcompat.v161
    implementation libs.material.v1110
    implementation libs.constraintlayout.v214
    implementation libs.okhttp              // Cliente HTTP
    implementation libs.json                // Parser JSON
}
```

### AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

**⚠️ IMPORTANTE:** A permissão de Internet é obrigatória para o aplicativo funcionar!

## 🌐 API Utilizada

O aplicativo utiliza a **AwesomeAPI**, uma API brasileira gratuita para cotações de moedas:

- **Endpoint:** `https://economia.awesomeapi.com.br/json/last/BRL-{MOEDA}`
- **Documentação:** https://docs.awesomeapi.com.br/api-de-moedas
- **Sem necessidade de chave de API**
- **Taxas atualizadas em tempo real**

## 🐛 Solução de Problemas

### Erro: "Erro ao obter taxa de câmbio"
- Verifique sua conexão com a Internet
- Certifique-se de que a permissão INTERNET está no AndroidManifest.xml
- Tente novamente após alguns segundos

### Erro: "Digite um valor válido em reais"
- Insira apenas números e ponto decimal
- Não deixe o campo vazio
- Exemplo válido: 100.50

### Erro de Compilação
- Execute: File > Invalidate Caches > Invalidate and Restart
- Certifique-se de ter conexão com a Internet para baixar dependências
- Verifique se o JDK 11+ está instalado

### Erro de Gradle Sync
- Aguarde o download das dependências concluir
- Verifique sua conexão com a Internet
- Tente: Build > Clean Project e depois Build > Rebuild Project

## 👨‍🎓 Informações Acadêmicas

- **Instituição:** FATEC Jahu
- **Curso:** Análise e Desenvolvimento de Sistemas
- **Disciplina:** PDM1 - Programação para Dispositivos Móveis I
- **Avaliação:** A2 (Avaliação 2)
- **Data de Entrega:** 12/12/2025 às 23:59
- **Pontuação:** 0 a 10

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte da avaliação da disciplina PDM1.

## 📞 Suporte

Em caso de dúvidas sobre o projeto, consulte:
- Os materiais fornecidos pelo professor
- A documentação oficial do Android: https://developer.android.com
- A documentação da AwesomeAPI: https://docs.awesomeapi.com.br

---

