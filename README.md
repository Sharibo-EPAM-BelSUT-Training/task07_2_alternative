# task07_2_alternative
Second part of seventh task in **[EPAM BelSUT Training](https://github.com/Sharibo-EPAM-BelSUT-Training)**. Alternative version.

I learned recursion, recursive file renaming. In this task, I wrote a recursion with the transfer of work to new threads.

***My idea was:** do not wait for the end of sub-loops to make the program run faster* (~2 seconds in this case, ha-ha).
***
<div align="center"><b>↓ How it works ↓</b></div>

***
The recursive loop is configured to define: a folder before it or a file. In any case, it creates a subcycle:
- **for a folder:** a new cycle will create a renamed folder (ADC_ + original name) in [```/out```](https://github.com/Sharibo-EPAM-BelSUT-Training/task07_2_alternative/tree/master/src/main/java/gmail/alexejkrawez/out) directory and then watch the contents of the folder.
- **for a file:** it will start changing the contents of the file and will save this file to [```/out```](https://github.com/Sharibo-EPAM-BelSUT-Training/task07_2_alternative/tree/master/src/main/java/gmail/alexejkrawez/out) directory under the edited name (with \_ADC\_ in name of file).


**Eventually:**
<dl>
  <dd>
    <b>1:</b> The first cycle starts looking at the main <code><a link href="https://github.com/Sharibo-EPAM-BelSUT-Training/task07_2_alternative/tree/master/src/main/java/gmail/alexejkrawez/in/ADC">ADC</a></code> folder.
If the loop finds a folder, then it creates a new subcycle (<b>2</b>), passing the path of this subfolder to it <i>and, without waiting for the subcycle to complete,</i> looks at the rest of the files / folders. For each folder it finds, it creates a subcycle in a new thread (<b>2</b>) <i>and looks further.</i> When a file is found, it creates a thread for operations on the file (<b>A</b>) and immediately <i>looks further.</i> When the traversal of the <code><a link href="https://github.com/Sharibo-EPAM-BelSUT-Training/task07_2_alternative/tree/master/src/main/java/gmail/alexejkrawez/in/ADC">ADC</a></code> folder finishes, the main thread exits.
  </dd>
  <dd>
    <b>2:</b> At the same time: each subcycle looks at its subfolder and creates a sub-subcycle (<b>3</b>) for folders or a new thread to work with each new file. It continues to run without waiting for the threads it has created to finish running.
  </dd>
  <dd>
    <b>3:</b> Recursion.))
  </dd>
  <dd>
    <b>A:</b> Each file is processed in an independent thread. Each cycle creates a destination folder, only then it finds the files and creates a thread for them for processing. Thus, work with the file <i>begins after the presence of the directory of its destination.</i>
  </dd>
</dl>

***

In this task the main ADC folder contains many subfolders, they contain sub-subfolders... and they contain *only one file each*. Therefore, I think creating threads for subfolders is a good solution, but creating threads for each file is a dubious solution.
With other initial data: if there are 100+ (a lot of) files in some folder, then the loop will need to sequentially process each file, which can be a long process. In such a case, splitting the file processing into separate threads can be a good solution.

**P.S.:** Such implicit recursion is more secure against stack overflow: the main loop does not know about the number of inner loops because it does not expect them = does not follow them.

**P.P.S.:** I understand that for small amounts of renamed data, the multithreaded implementation of the program is redundant.
