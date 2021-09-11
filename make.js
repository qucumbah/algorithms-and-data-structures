const cp = require('child_process');
const fs = require('fs');
const path = require('path');

function build() {
  cp.execSync('javac -d build -encoding utf8 Main.java', { stdio: 'inherit' });
}

function run() {
  cp.execSync('java -Dfile.encoding=UTF8 Main', { cwd: 'build', stdio: 'inherit' });
}

function buildAndRun() {
  build();
  run();
}

function create() {
  const className = process.argv[3];
  if (!className) {
    console.error('Provide class name');
    process.exit(1);
  }

  const dirPath = path.join('prep', className.toLowerCase());
  if (fs.existsSync(dirPath)) {
    console.error(`Directory ${className.toLowerCase()} already exists`);
    process.exit(1);
  }

  fs.mkdirSync(dirPath);

  const exampleDirName = path.join('prep', 'example');

  const classPath = path.join(dirPath, `${className}.java`)
  const classTestPath = path.join(dirPath, `${className}Test.java`);

  fs.copyFileSync(
    path.join(exampleDirName, 'Example.java'),
    classPath,
  );
  fs.copyFileSync(
    path.join(exampleDirName, 'ExampleTest.java'),
    classTestPath,
  );

  fs.writeFileSync(
    classPath,
    fs.readFileSync(classPath, 'utf-8')
      .replace(/example/g, className.toLowerCase())
      .replace(/Example/g, className)
  );
  fs.writeFileSync(
    classTestPath,
    fs.readFileSync(classTestPath, 'utf-8')
      .replace(/example/g, className.toLowerCase())
      .replace(/Example/g, className)
  );
}

const command = process.argv[2];
const commands = {
  build,
  run,
  buildAndRun,
  create,
};

if (!commands[command]) {
  console.error(`Unknown command: ${command}`);
  process.exit(1);
}

try {
  commands[command]();
} catch {

}
