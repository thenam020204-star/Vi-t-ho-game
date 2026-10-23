"""Tien ich: nap bundle + TypeTreeGenerator tu libil2cpp/metadata. Dung trong cac script phan tich.
(TypeTreeGeneratorAPI chi biet ten assembly khong co '.dll', UnityPy lai truyen co '.dll' -> bao loi; sua o day.)"""
import UnityPy
from UnityPy.helpers.TypeTreeGenerator import TypeTreeGenerator as _G
from UnityPy.helpers.TypeTreeNode import TypeTreeNode

UNITY = "2022.3.62f3"


class Gen(_G):
    def get_nodes_up(self, assembly, fullname):
        key = (assembly, fullname)
        if key in self.cache:
            return self.cache[key]
        if assembly.endswith('.dll'):
            assembly = assembly[:-4]
        base = self.get_nodes(assembly, fullname)
        node = TypeTreeNode.from_list([TypeTreeNode(n.m_Level, n.m_Type, n.m_Name, 0, 0, m_MetaFlag=n.m_MetaFlag) for n in base])
        self.cache[key] = node
        return node


def load(bundle, so, meta):
    env = UnityPy.load(bundle)
    gen = Gen(UNITY)
    gen.load_il2cpp(open(so, 'rb').read(), open(meta, 'rb').read())
    env.typetree_generator = gen
    return env, gen
