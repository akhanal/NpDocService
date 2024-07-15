import os
import tkinter as tk
from tkinter import filedialog, messagebox, scrolledtext

def create_jumbo_text(folder_path, file_types, exclude_folders):
    jumbo_text = ""

    for root, dirs, files in os.walk(folder_path):
        # Compute relative path from the root folder
        relative_root = os.path.relpath(root, folder_path)

        # Exclude specified folders, hidden folders, and specific folder names
        dirs[:] = [d for d in dirs if os.path.join(relative_root, d) not in exclude_folders and not d.startswith('.') and d not in ['node_modules', 'target']]

        for file in files:
            if any(file.endswith(ft) for ft in file_types):
                file_path = os.path.join(root, file)
                # Get relative path from the root folder
                relative_file_path = os.path.relpath(file_path, folder_path)
                with open(file_path, 'r', encoding='utf-8') as f:
                    file_content = f.read()
                jumbo_text += f"{relative_file_path}\n{file_content}\n\n"

    return jumbo_text

def select_root_folder():
    folder_selected = filedialog.askdirectory()
    root_folder_entry.delete(0, tk.END)
    root_folder_entry.insert(0, folder_selected)

def add_exclude_folder():
    folder_selected = filedialog.askdirectory()
    root_path = root_folder_entry.get()
    if folder_selected and root_path:
        relative_path = os.path.relpath(folder_selected, root_path)
        exclude_folders_listbox.insert(tk.END, relative_path)

def generate_jumbo_text():
    folder_path = root_folder_entry.get()
    file_types = include_file_types_entry.get().split(',')
    exclude_folders = [exclude_folders_listbox.get(idx) for idx in range(exclude_folders_listbox.size())]

    if not folder_path or not file_types:
        messagebox.showerror("Error", "Please specify the root folder and file types to include.")
        return

    jumbo_text = create_jumbo_text(folder_path, file_types, exclude_folders)

    # Save jumbo text to a file
    with open('jumbo_text.txt', 'w', encoding='utf-8') as jumbo_file:
        jumbo_file.write(jumbo_text)

    messagebox.showinfo("Success", "Jumbo text created successfully.")
    result_textbox.delete(1.0, tk.END)
    result_textbox.insert(tk.END, jumbo_text)

# Create the main window
root = tk.Tk()
root.title("Jumbo Text Generator")

# Root folder selection
tk.Label(root, text="Select Root Folder:").grid(row=0, column=0, padx=10, pady=10)
root_folder_entry = tk.Entry(root, width=50)
root_folder_entry.grid(row=0, column=1, padx=10, pady=10)
root_folder_button = tk.Button(root, text="Browse", command=select_root_folder)
root_folder_button.grid(row=0, column=2, padx=10, pady=10)

# File types to include
tk.Label(root, text="Include File Types (comma-separated):").grid(row=1, column=0, padx=10, pady=10)
include_file_types_entry = tk.Entry(root, width=50)
include_file_types_entry.grid(row=1, column=1, padx=10, pady=10)

# Folders to exclude
tk.Label(root, text="Folders to Exclude:").grid(row=2, column=0, padx=10, pady=10)
exclude_folders_listbox = tk.Listbox(root, selectmode=tk.MULTIPLE, width=50, height=5)
exclude_folders_listbox.grid(row=2, column=1, padx=10, pady=10)
add_exclude_folder_button = tk.Button(root, text="Add Folder", command=add_exclude_folder)
add_exclude_folder_button.grid(row=2, column=2, padx=10, pady=10)

# Generate button
generate_button = tk.Button(root, text="Generate Jumbo Text", command=generate_jumbo_text)
generate_button.grid(row=3, column=1, padx=10, pady=10)

# Result textbox
tk.Label(root, text="Generated Jumbo Text:").grid(row=4, column=0, padx=10, pady=10)
result_textbox = scrolledtext.ScrolledText(root, width=80, height=20)
result_textbox.grid(row=4, column=1, columnspan=2, padx=10, pady=10)

# Run the main loop
root.mainloop()
