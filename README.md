# Trabajo Practico Final para la materia Algoritmos y Programacion III - Catedra Corsi/Essaya
Facultad de Ingenieria de la Universidad de Buenos Aires - 1 Cuatrimestre 2023. <br>
Integrantes:
- Martina Lourdes Rey (109067)
- Maria Mercedes Slepowron Majowiecki (109454) 
- Agustina Landi (107850)

Informe

Para este Trabajo Práctico desarrollado a lo largo de la cursada implementamos un calendario haciendo referencia al calendario de Google Calendar. El diseño del calendario virtual se centra en ofrecer una interfaz de usuario fácil de usar, donde se pueden agregar, modificar y eliminar tanto eventos como tareas con sus respectivas duraciones y alarmas. El calendario presenta una vista de tipo mensual, semanal o diaria, donde podes visualizar, agregar y administrar tus eventos y tareas a través de controles como por ejemplo botones, checkboxes y dropdowns entre otros. Además, el calendario cuenta con la capacidad de guardar su estado, de modo que si el usuario desea recuperar las modificaciones hechas lo puede hacer.

El objetivo principal de este trabajo práctico era trabajar con el paradigma de la Programación Orientada a Objetos. Por esto mismo, decidimos dividir las responsabilidades en diferentes clases, las principales son: Calendario, Actividad, Evento y Tarea. Principalmente la clase Calendario se encarga de operar a grandes rasgos con las clases de Actividad, Evento y Tarea, mientras que estas tienen el trabajo de setear sus atributos y características como por ejemplo su duración, repetición y alarmas. Por otro lado, también, contamos con la implementación de la GUI, que tiene como clases principales los controladores con sus respectivas vistas ya que según las operaciones del modelo realizan los cambios necesarios.

Para implementar correctamente el paradigma de POO utilizamos algunos de los patrones de diseños dictados por la cátedra. Entre ellos, utilizamos principalmente el Strategy Pattern, el cual nos permitió dividir bien las responsabilidades entre clases y favorecer la composición por sobre la herencia. También, utilizamos el patrón de MVC: Model-View-Controller para implementar la interfaz gráfica de forma que podamos dividir las tareas tanto del modelo, vista y controlador.


