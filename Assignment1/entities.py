import point


class Background:
    def __init__(self, name, imgs):
        self.name = name
        self.imgs = imgs
        self.current_img = 0

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

class MinerNotFull:
    def __init__(self, name, resource_limit, position, rate, imgs,
                 animation_rate):
        self.name = name
        self.position = position
        self.rate = rate
        self.imgs = imgs
        self.current_img = 0
        self.resource_limit = resource_limit
        self.resource_count = 0
        self.animation_rate = animation_rate
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class MinerFull:
    def __init__(self, name, resource_limit, position, rate, imgs,
                 animation_rate):
        self.name = name
        self.position = position
        self.rate = rate
        self.imgs = imgs
        self.current_img = 0
        self.resource_limit = resource_limit
        self.resource_count = resource_limit
        self.animation_rate = animation_rate
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class Vein:
    def __init__(self, name, rate, position, imgs, resource_distance=1):
        self.name = name
        self.position = position
        self.rate = rate
        self.imgs = imgs
        self.current_img = 0
        self.resource_distance = resource_distance
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class Ore:
    def __init__(self, name, position, imgs, rate=5000):
        self.name = name
        self.position = position
        self.imgs = imgs
        self.current_img = 0
        self.rate = rate
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class Blacksmith:
    def __init__(self, name, position, imgs, resource_limit, rate,
                 resource_distance=1):
        self.name = name
        self.position = position
        self.imgs = imgs
        self.current_img = 0
        self.resource_limit = resource_limit
        self.resource_count = 0
        self.rate = rate
        self.resource_distance = resource_distance
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class Obstacle:
    def __init__(self, name, position, imgs):
        self.name = name
        self.position = position
        self.imgs = imgs
        self.current_img = 0

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

class OreBlob:
    def __init__(self, name, position, rate, imgs, animation_rate):
        self.name = name
        self.position = position
        self.rate = rate
        self.imgs = imgs
        self.current_img = 0
        self.animation_rate = animation_rate
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]

    def get_rate(self):
        return self.rate

class Quake:
    def __init__(self, name, position, imgs, animation_rate):
        self.name = name
        self.position = position
        self.imgs = imgs
        self.current_img = 0
        self.animation_rate = animation_rate
        self.pending_actions = []

    def set_position(self, point):
        self.position = point

    def get_position(self):
        return self.position

    def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]



#Blacksmith, MinerFull, MinerNotFull
def set_resource_count(entity, n):
    entity.resource_count = n

#Blacksmith, MinerFull, MinerNotFull
def get_resource_count(entity):
    return entity.resource_count

#Blacksmith, MinerFull, MinerNotFull
def get_resource_limit(entity):
    return entity.resource_limit

#Blacksmith, Vein
def get_resource_distance(entity):
    return entity.resource_distance

#All
def get_name(entity):
    return entity.name

#Quake, Oreblob, MinerFull, MinerNotFull
def get_animation_rate(entity):
    return entity.animation_rate

#All except Background and Obstacle
def remove_pending_action(entity, action):
    if hasattr(entity, "pending_actions"):
        entity.pending_actions.remove(action)

#All except Background and Obstacle
def add_pending_action(entity, action):
    if hasattr(entity, "pending_actions"):
        entity.pending_actions.append(action)

#All except Background and Obstacle
def get_pending_actions(entity):
    if hasattr(entity, "pending_actions"):
        return entity.pending_actions 
    else:
        return []

#All except Background and Obstacle
def clear_pending_actions(entity):
    if hasattr(entity, "pending_actions"):
        entity.pending_actions = []

#All
def next_image(entity):
    entity.current_img = (entity.current_img + 1) % len(entity.imgs)


# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.

#Move into all objects.
def entity_string(entity):
    if isinstance(entity, MinerNotFull):
        return ' '.join(['miner', entity.name, str(entity.position.x),
                         str(entity.position.y), str(entity.resource_limit),
                         str(entity.rate), str(entity.animation_rate)])
    elif isinstance(entity, Vein):
        return ' '.join(['vein', entity.name, str(entity.position.x),
                         str(entity.position.y), str(entity.rate),
                         str(entity.resource_distance)])
    elif isinstance(entity, Ore):
        return ' '.join(['ore', entity.name, str(entity.position.x),
                         str(entity.position.y), str(entity.rate)])
    elif isinstance(entity, Blacksmith):
        return ' '.join(['blacksmith', entity.name, str(entity.position.x),
                         str(entity.position.y), str(entity.resource_limit),
                         str(entity.rate), str(entity.resource_distance)])
    elif isinstance(entity, Obstacle):
        return ' '.join(['obstacle', entity.name, str(entity.position.x),
                         str(entity.position.y)])
    else:
        return 'unknown'

