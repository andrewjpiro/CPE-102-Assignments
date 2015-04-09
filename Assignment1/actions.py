import math
import random

import pygame

import entities
import worldmodel
import point
import image_store


BLOB_RATE_SCALE = 4
BLOB_ANIMATION_RATE_SCALE = 50
BLOB_ANIMATION_MIN = 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000


def sign(x):
    if x < 0:
        return -1
    elif x > 0:
        return 1
    else:
        return 0

def adjacent(pt1, pt2):
    return ((pt1.x == pt2.x and abs(pt1.y - pt2.y) == 1) or
            (pt1.y == pt2.y and abs(pt1.x - pt2.x) == 1))




# combine with other try miner transforms
def try_transform_miner(world, entity, transform):
    new_entity = transform()
    if entity != new_entity:
        world.clear_pending_actions(entity)
        world.remove_entity_at(entity.get_position())
        world.add_entity(new_entity)
        world.schedule_animation(new_entity)

    return new_entity

def create_miner_action(world, entity, image_store):
    entity.create_action(world, image_store)

# world model
def create_entity_death_action(world, entity):
    def action(current_ticks):
        entity.remove_pending_action(action)
        pt = entity.get_position()
        world.remove_entity(entity)
        return [pt]

    return action

def create_blob(world, name, pt, rate, ticks, i_store):
    blob = entities.OreBlob(name, pt, rate,
                            image_store.get_images(i_store, 'blob'),
                            random.randint(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)
                            * BLOB_ANIMATION_RATE_SCALE)
    blob.schedule(world, ticks, i_store)
    return blob


def create_ore(world, name, pt, ticks, i_store):
    ore = entities.Ore(name, pt, image_store.get_images(i_store, 'ore'),
                       random.randint(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX))
    ore.schedule(world, ticks, i_store)

    return ore


def create_quake(world, pt, ticks, i_store):
    quake = entities.Quake("quake", pt,
                           image_store.get_images(i_store, 'quake'), QUAKE_ANIMATION_RATE)
    quake.schedule(world, ticks)
    return quake



def create_vein(world, name, pt, ticks, i_store):
    vein = entities.Vein("vein" + name,
                         random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX),
                         pt, image_store.get_images(i_store, 'vein'))
    return vein
