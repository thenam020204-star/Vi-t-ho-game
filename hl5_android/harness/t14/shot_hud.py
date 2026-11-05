"""T14b: chụp HUD mới. shot_hud.py W H OUT_PNG"""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
r = mk(int(sys.argv[1]), int(sys.argv[2]), 'new', 'sh'); to_play(r, 1300); r.step(3); shot(r, sys.argv[3])
