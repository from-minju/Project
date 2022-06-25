using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;

namespace AsyncGUISImpleClient
{
    public class AsyncObject
    {
        public byte[] Buffer;
        public Socket workingSocket;
        public readonly int BufferSize;

        public string userName { get; set; }

        public AsyncObject(string name)
        {
            this.userName = name;
        }

        public AsyncObject(int bufferSize)
        {
            BufferSize = bufferSize;
            Buffer = new byte[BufferSize];

        }
        public void clearBuffer()
        {
            Array.Clear(Buffer, 0, BufferSize);
        }
        
    }
}
