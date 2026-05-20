
### ✅ Cambios realizados:

1. **Sección "👥 Equipo de Desarrollo"** con información de los integrantes y roles
2. **"Detalle de contribuciones"** desglosado por cada miembro con iconos representativos
3. **Roles correctamente asignados** según lo que me indicaste:

   - García Ugarte Javier: Programador principal, QA/testing, Desarrollador
   - González Ruiz Víctor Manuel: QA/testing, Documentador, Desarrollador
   - Mares Pacheco Laura Sarai: Integrador, Documentador


# 🃏 UNO - Juego de Cartas en Java

![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)
![Swing](https://img.shields.io/badge/GUI-Swing-orange.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)
![Version](https://img.shields.io/badge/Version-5.0-brightgreen.svg)

## 📖 Descripción

Este es una versión del popular juego de cartas **UNO**, desarrollado íntegramente en Java con interfaz gráfica Swing. El juego enfrenta a un jugador humano contra tres oponentes controlados por inteligencia artificial básica para la clase de Construcción y Evolución de Software. 


## ✨ Características

- 🎮 **Modo de juego**: 1 jugador humano vs 3 IA
- 🃏 **Cartas completas**: 
  - Números 0-9
  - Cartas de acción (+2, Salto, Reversa)
  - Comodines (Cambio de color, +4)
- 🎨 **Interfaz gráfica** con sprites personalizados
- 🧠 **IA básica** para los oponentes
- 🔊 **Sistema de penalización UNO**
- 🔄 **Gestión de turnos** con efectos de cartas
- 📝 **Log de acciones** en tiempo real

## 🎯 Reglas del Juego

1. Cada jugador comienza con **7 cartas**
2. El jugador debe coincidir con la carta central en **color** o **número**
3. Las cartas especiales tienen efectos únicos:
   - **+2**: El siguiente jugador roba 2 cartas
   - **Salto**: El siguiente jugador pierde su turno
   - **Reversa**: Cambia la dirección del juego
   - **Comodín**: Cambia el color y el siguiente jugador roba 4 cartas
4. Cuando te quede **1 carta**, debes decir "UNO"
5. Si te descubren sin decirlo, recibes 2 cartas de penalización
6. **¡Gana el primero en quedarse sin cartas!**

## 🚀 Instalación y Ejecución

### Requisitos Previos
- JDK 17 o superior
- Git (opcional, para clonar)
- Eclipse o NetBeans (Versión actual)

### Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/uno-juego.git
cd uno-juego
