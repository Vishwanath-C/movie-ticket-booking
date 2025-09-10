import detect from 'detect-port';
import { spawn } from 'child_process';

const DEFAULT_PORT = 5176;

const port = await detect(DEFAULT_PORT);

if (port === DEFAULT_PORT) {
  console.log(`Starting Vite on default port ${DEFAULT_PORT}`);
} else {
  console.log(`Port ${DEFAULT_PORT} is in use. Starting Vite on free port ${port}`);
}

const vite = spawn('npx', ['vite', '--port', port], { stdio: 'inherit', shell: true });

vite.on('close', code => {
  process.exit(code);
});
