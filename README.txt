
I developed the To-Do List App adhering to the MVVM (Model-View-ViewModel) architecture.

To persistently store the task data, I utilized a SharedPreference() instance. Each task was stored individually with a unique key, and the list of these keys was stored to facilitate task retrieval and display. Additionally, I stored the isEditing value for each task to manage its editing state effectively.