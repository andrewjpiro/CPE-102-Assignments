import pygame

DEFAULT_IMAGE_NAME = 'background_default'
DEFAULT_IMAGE_COLOR = (128, 128, 128, 0)

class ImageStore():

    def __init__(self, filename, tile_width, tile_height):
        self.images = {}
        with open(filename) as fstr:
            for line in fstr:
                process_image_line(self.images, line)

        if DEFAULT_IMAGE_NAME not in self.images:
            default_image = create_default_image(tile_width, tile_height)
            self.images[DEFAULT_IMAGE_NAME] = [default_image]


def create_default_image(tile_width, tile_height):
    surf = pygame.Surface((tile_width, tile_height))
    surf.fill(DEFAULT_IMAGE_COLOR)
    return surf


def process_image_line(images, line):
    attrs = line.split()
    if len(attrs) >= 2:
        key = attrs[0]
        img = pygame.image.load(attrs[1]).convert()

        if img:
            imgs = get_images_internal(images, key)
            imgs.append(img)
            images[key] = imgs

            if len(attrs) == 6:
                r = int(attrs[2])
                g = int(attrs[3])
                b = int(attrs[4])
                a = int(attrs[5])
                img.set_colorkey(pygame.Color(r, g, b, a))

def get_images_internal(images, key):
    if key in images:
        return images[key]
    else:
        return []

def get_images(images, key):
    if key in images:
        return images[key]
    else:
        return images[DEFAULT_IMAGE_NAME]
