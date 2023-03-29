import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('flutter_todo_list_kiram/platform');

  @override
  void initState() {
    initDatabase();
    super.initState();
  }

  List<Widget> dummyWidgets = [];

  void buildTaskList(List<ToDo> todos) {
    List<Widget> widgets = [];
    for (final todo in todos) {
      widgets.add(InkWell(
        onTap: () => completeTask(todo),
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
          margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 4),
          decoration: BoxDecoration(
            border: Border.all(color: Colors.black12, width: 1),
          ),
          child: Row(
            children: [
              CircleAvatar(
                child: Text(todo.task![0].toUpperCase()),
              ),
              SizedBox(width: 16),
              Text(
                todo.task!,
                style: TextStyle(
                  fontSize: 16,
                  decoration: todo.status == 1 ? TextDecoration.lineThrough : null,
                ),
              ),
            ],
          ),
        ),
      ));
    }
    setState(() {
      dummyWidgets = widgets;
    });
  }

  Future<void> initDatabase() async {
    List<ToDo> todos = [];
    final List<dynamic> getDb = await platform.invokeMethod('init_database');
    refreshData(todos, getDb);
  }

  void refreshData (List<ToDo> todos, List<dynamic> database) {
    for (int i = 0; i < database.length; i++) {
      final item = database[i];
      todos.add(ToDo(
        id: int.parse(item["id"]),
        task: item["task"],
        status: int.parse(item["status"]),
      ));
    }
    buildTaskList(todos);
  }

  Future<void> addNewTask() async {
    List<ToDo> todos = [];
    final List<dynamic> insertDb = await platform.invokeMethod('insert_database', {
      'task': textFieldController!.text,
    });
    refreshData(todos, insertDb);
  }

  Future<void> completeTask(ToDo todo) async {
    List<ToDo> todos = [];
    final List<dynamic> updateDb = await platform.invokeMethod('update_database', {
      'id': todo.id,
      'task': todo.task,
    });
    refreshData(todos, updateDb);
  }

  final TextEditingController? textFieldController = TextEditingController();

  Future<void> buildToDoFormDialog(BuildContext context) async {
    return showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Add new ToDo Item'),
          content: TextField(
            onChanged: (value) {},
            controller: textFieldController,
            decoration: const InputDecoration(hintText: "Type your new ToDo"),
          ),
          actions: [
            TextButton(
              onPressed: () {
                addNewTask();
                Navigator.pop(context);
              },
              child: const Text("Add Task"),
            )
          ],
        );
      },
    );
  }

  @override
  void dispose() {
    textFieldController!.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: dummyWidgets,
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => buildToDoFormDialog(context),
        tooltip: 'Add Task',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class ToDo {
  ToDo({
    this.id,
    this.task,
    this.status,
  });

  int? id;
  String? task;
  int? status;
}
