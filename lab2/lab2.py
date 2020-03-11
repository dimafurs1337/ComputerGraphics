from PIL import Image
from glob import glob
from sys import argv
from os.path import join

path = argv[1]
formats = ("*.jpg", "*.gif", "*.tif", "*.bmp", "*.png", "*.pcx")
mode_to_bpp = {'1':1, 'L':8, 'P':8, 'RGB':24, 'RGBA':32, 'CMYK':32, 'YCbCr':24, 'I':32, 'F':32}
file_names = []

for files in formats:
    file_names.extend(glob(join(path,files)))

print("-"*90)
print("File name \t\t    Image size \t\t     Dpi     Bit depth        Compression ")
print("-"*90)
for f in file_names:
    img = Image.open(f)
    print('{:<26s}{:>12s}{:>18s}{:>14s}{:>18s}'.format(f[f.rfind("\\")+1:],
                                                str(img.size), 
                                                str(tuple(int(el) for el in img.info.get('dpi', ""))) if (img.info.get('dpi', '') != (0,0) and len(img.info.get('dpi', ''))!=0) else "", 
                                                str(mode_to_bpp[img.mode]),
                                                str(img.info.get('compression', "")) if img.info.get("compression", "") != 0 else "" 
                                                ))

