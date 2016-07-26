package edu.byu.cs.superasteroids.database;

/**
 * Created by cole on 7/18/16.
 */
public class GameDBSchema {

    public static final class LevelObjectsTable {
        public static final String NAME = "level_objects";

        public static final class Cols {
            public static final String ID = "id";
            public static final String IMAGE = "image";
        }
    }

    public static final class AsteroidsTable {
        public static final String NAME = "asteroids";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String IMAGE = "image";
            public static final String IMAGE_WIDTH = "image_width";
            public static final String IMAGE_HEIGHT = "image_height";
            public static final String TYPE = "type";
        }
    }

    public static final class LevelsTable {
        public static final String NAME = "levels";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NUMBER = "number";
            public static final String TITLE = "title";
            public static final String HINT = "hint";
            public static final String WIDTH = "width";
            public static final String HEIGHT = "height";
            public static final String MUSIC = "music";
        }
    }

    public static final class LevelsLevelObjectsJoinTable {
        public static final String NAME = "levels_level_objects_join";

        public static final class Cols {
            public static final String LEVEL_ID = "level_id";
            public static final String OBJECT_ID = "object_id";
            public static final String OBJECT_POSITION_X = "object_positionX";
            public static final String OBJECT_POSITION_Y = "object_positionY";
            public static final String OBJECT_SCALE = "object_scale";
        }
    }

    public static final class LevelsAsteroidsJoinTable {
        public static final String NAME = "levels_asteroids_join";

        public static final class Cols {
            public static final String LEVEL_ID = "level_id";
            public static final String ASTEROID_ID = "asteroid_id";
            public static final String ASTEROID_QUANTITY = "asteroid_quantity";
        }
    }

    public static final class MainBodiesTable {
        public static final String NAME = "main_bodies";

        public static final class Cols {
            public static final String ID = "id";
            public static final String CANNON_ATTACH_X = "cannon_attach_x";
            public static final String ENGINE_ATTACH_X = "engine_attach_x";
            public static final String EXTRA_ATTACH_X = "extra_attach_x";
            public static final String CANNON_ATTACH_Y = "cannon_attach_y";
            public static final String ENGINE_ATTACH_Y = "engine_attach_y";
            public static final String EXTRA_ATTACH_Y = "extra_attach_y";
            public static final String IMAGE = "image";
            public static final String IMAGE_WIDTH = "image_width";
            public static final String IMAGE_HEIGHT = "image_height";
        }
    }

    public static final class CannonsTable {
        public static final String NAME = "cannons";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ATTACH_POINT_X = "attach_point_x";
            public static final String ATTACH_POINT_Y = "attach_point_y";
            public static final String EMIT_POINT_X = "emit_point_x";
            public static final String EMIT_POINT_Y = "emit_point_y";
            public static final String IMAGE = "image";
            public static final String IMAGE_WIDTH = "image_width";
            public static final String IMAGE_HEIGHT = "image_height";
            public static final String ATTACK_IMAGE = "attack_image";
            public static final String ATTACK_IMAGE_WIDTH = "attack_image_width";
            public static final String ATTACK_IMAGE_HEIGHT = "attack_image_height";
            public static final String ATTACK_SOUND = "attack_sound";
            public static final String DAMAGE = "damage";
        }
    }

    public static final class ExtraPartsTable {
        public static final String NAME = "extra_parts";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ATTACH_POINT_X = "attach_point_x";
            public static final String ATTACH_POINT_Y = "attach_point_y";
            public static final String IMAGE = "image";
            public static final String IMAGE_WIDTH = "image_width";
            public static final String IMAGE_HEIGHT = "image_height";
        }
    }

    public static final class EnginesTable {
        public static final String NAME = "engines";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ATTACH_POINT_X = "attach_point_x";
            public static final String ATTACH_POINT_Y = "attach_point_y";
            public static final String IMAGE = "image";
            public static final String IMAGE_WIDTH = "image_width";
            public static final String IMAGE_HEIGHT = "image_height";
            public static final String BASE_SPEED = "base_speed";
            public static final String BASE_TURN_RATE = "base_turn_rate";
        }
    }

    public static final class PowerCoresTable {
        public static final String NAME = "power_cores";

        public static final class Cols {
            public static final String ID = "id";
            public static final String CANNON_BOOST = "cannon_boost";
            public static final String ENGINE_BOOST = "engine_boost";
            public static final String IMAGE = "image";
        }
    }
}