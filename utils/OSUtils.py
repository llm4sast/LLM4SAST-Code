import sys

def IsWindows() -> bool:
    return sys.platform == "win32"

def IsMac() -> bool:
    return sys.platform == 'darwin'

def IsLinux() -> bool:
    return sys.platform.startswith('linux')

