from tkinter import Tk, Button, Scrollbar, LEFT, BOTTOM


def new_file():
    for r in range(100):
        for c in range(100):
            Button(root).grid(row=r, column=c)
    """
    Scrollbar(root).pack(side=LEFT)
    Scrollbar(root).pack(side=BOTTOM)
    """


def open_file():
    pass


def save_file():
    pass


def floor_click():
    pass


root = Tk()
root.title("Map Editor")
Button(root, text="New File", command=new_file).grid(row=0, column=0)
Button(root, text="Open File", command=open_file).grid(row=0, column=1)
Button(root, text="Open File", command=save_file).grid(row=0, column=2)
root.mainloop()
